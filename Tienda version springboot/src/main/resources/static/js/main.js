/* ==========================================================================
   TIENDA PARKING - main.js
   Búsqueda en tiempo real y exportación de Reporte General Consolidado (.xlsx)
   ========================================================================== */

function inicializarApp() {
    console.log('[TiendaParking] ✅ main.js inicializado.');

    // ── 1. BUSCADOR EN TIEMPO REAL ──────────────────────────────────────────
    var searchInputs = document.querySelectorAll('.search-input');
    for (var i = 0; i < searchInputs.length; i++) {
        (function(input) {
            input.addEventListener('input', function() {
                var termino = input.value.toLowerCase().trim();
                var filas = document.querySelectorAll('table tbody tr');
                for (var f = 0; f < filas.length; f++) {
                    var fila = filas[f];
                    // Omitir filas informativas sin datos reales (solo 1 celda colspan)
                    if (fila.cells.length <= 1) continue;
                    var textoFila = fila.innerText.toLowerCase();
                    fila.style.display = (textoFila.indexOf(termino) >= 0) ? '' : 'none';
                }
            });
        })(searchInputs[i]);
    }

    // ── 2. BOTÓN EXPORTAR EXCEL GENERAL (DASHBOARD) ──────────────────────────
    var btnExport = document.getElementById('btn-export');
    if (btnExport) {
        btnExport.addEventListener('click', function(e) {
            e.preventDefault();
            exportarReporteGeneralExcel();
        });
    }
}

// Inicializar cuando el DOM esté listo
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', inicializarApp);
} else {
    inicializarApp();
}

/* ============================================================
   EXPORTACIÓN GENERAL MULTI-HOJA CON SHEETJS (.xlsx)
   ============================================================ */
function exportarReporteGeneralExcel() {
    if (typeof XLSX === 'undefined') {
        alert('SheetJS (xlsx) no está disponible. Verifica la conexión a internet e intenta nuevamente.');
        return;
    }

    var btnExport = document.getElementById('btn-export');
    var textoOriginal = btnExport ? btnExport.innerHTML : '';
    if (btnExport) {
        btnExport.disabled = true;
        btnExport.innerHTML = 'Generando Reporte...';
    }

    fetch('/api/reporte-general')
        .then(function(res) {
            if (!res.ok) throw new Error('Error en el servidor al obtener los datos.');
            return res.json();
        })
        .then(function(data) {
            generarLibroExcelConsolidado(data);
        })
        .catch(function(err) {
            console.error('[TiendaParking] Error al exportar:', err);
            alert('Error al generar el reporte: ' + err.message);
        })
        .finally(function() {
            if (btnExport) {
                btnExport.disabled = false;
                btnExport.innerHTML = textoOriginal;
            }
        });
}

function generarLibroExcelConsolidado(datos) {
    var wb = XLSX.utils.book_new();
    wb.Props = {
        Title: 'Reporte General Tienda Parking',
        Subject: 'Consolidado de Módulos (Viajes, Carros, Motores, Choferes, Pasajeros)',
        Author: 'Tienda Parking - SGP',
        CreatedDate: new Date()
    };

    var fechaHoy = new Date();
    var fechaStr = fechaHoy.toLocaleDateString('es-CO', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' });
    var fechaSlug = fechaHoy.toISOString().slice(0, 10);

    // Paleta de estilos corporativos
    var COLOR_BG_TITULO = '09090B'; // Negro Zinc-950
    var COLOR_FG_TITULO = 'FFFFFF';
    var COLOR_BG_SUBTIT = '27272A'; // Gris oscuro Zinc-800
    var COLOR_FG_SUBTIT = 'A1A1AA';
    var COLOR_BG_HEADER = '18181B'; // Encabezado de columnas
    var COLOR_FG_HEADER = 'FFFFFF';
    var COLOR_ALT       = 'F4F4F5'; // Celdas alternas
    var COLOR_BORDER    = 'E4E4E7';

    function crearHojaEstilizada(tituloModulo, headers, dataRows) {
        var aoa = [
            ['TIENDA PARKING — ' + tituloModulo.toUpperCase()],
            ['Exportado el: ' + fechaStr + '   |   Base de Datos: MySQL (tienda_parking)'],
            [],
            headers
        ];

        for (var i = 0; i < dataRows.length; i++) {
            aoa.push(dataRows[i]);
        }

        var ws = XLSX.utils.aoa_to_sheet(aoa);
        var numCols = headers.length;

        function cellStyle(opts) {
            return {
                font:  { name: 'Calibri', sz: opts.sz || 11, bold: !!opts.bold, color: { rgb: opts.fg || '27272A' } },
                fill:  opts.bg ? { fgColor: { rgb: opts.bg }, patternType: 'solid' } : { patternType: 'none' },
                alignment: { horizontal: opts.align || 'left', vertical: 'center', wrapText: false },
                border: {
                    top:    { style: 'thin', color: { rgb: COLOR_BORDER } },
                    bottom: { style: 'thin', color: { rgb: COLOR_BORDER } },
                    left:   { style: 'thin', color: { rgb: COLOR_BORDER } },
                    right:  { style: 'thin', color: { rgb: COLOR_BORDER } }
                }
            };
        }

        function setStyle(r, c, style) {
            var ref = XLSX.utils.encode_cell({ r: r, c: c });
            if (!ws[ref]) ws[ref] = { v: '', t: 's' };
            ws[ref].s = style;
        }

        // Título Fila 0
        for (var c = 0; c < numCols; c++) setStyle(0, c, cellStyle({ bold: true, sz: 14, bg: COLOR_BG_TITULO, fg: COLOR_FG_TITULO, align: 'center' }));
        // Subtítulo Fila 1
        for (var c = 0; c < numCols; c++) setStyle(1, c, cellStyle({ sz: 9, bg: COLOR_BG_SUBTIT, fg: COLOR_FG_SUBTIT, align: 'center' }));
        // Separador Fila 2
        for (var c = 0; c < numCols; c++) setStyle(2, c, cellStyle({ bg: 'FFFFFF' }));
        // Headers Fila 3
        for (var c = 0; c < numCols; c++) setStyle(3, c, cellStyle({ bold: true, sz: 11, bg: COLOR_BG_HEADER, fg: COLOR_FG_HEADER, align: 'center' }));
        // Datos Filas 4+
        for (var r = 0; r < dataRows.length; r++) {
            var bg = (r % 2 === 1) ? COLOR_ALT : 'FFFFFF';
            for (var c = 0; c < numCols; c++) {
                setStyle(4 + r, c, cellStyle({ sz: 10, bg: bg, align: c === 0 ? 'center' : 'left' }));
            }
        }

        // Merges de encabezado
        if (!ws['!merges']) ws['!merges'] = [];
        if (numCols > 1) {
            ws['!merges'].push({ s: { r: 0, c: 0 }, e: { r: 0, c: numCols - 1 } });
            ws['!merges'].push({ s: { r: 1, c: 0 }, e: { r: 1, c: numCols - 1 } });
            ws['!merges'].push({ s: { r: 2, c: 0 }, e: { r: 2, c: numCols - 1 } });
        }

        // Anchos de columna automáticos
        ws['!cols'] = headers.map(function(h, idx) {
            var max = h.length;
            for (var r = 0; r < dataRows.length; r++) {
                var val = dataRows[r][idx];
                if (val !== undefined && val !== null && String(val).length > max) {
                    max = String(val).length;
                }
            }
            return { wch: Math.min(Math.max(max + 4, 12), 45) };
        });

        // Alturas de filas
        ws['!rows'] = [{ hpt: 34 }, { hpt: 16 }, { hpt: 8 }, { hpt: 22 }];
        for (var r = 0; r < dataRows.length; r++) ws['!rows'].push({ hpt: 18 });

        return ws;
    }

    // 1. Hoja: Viajes Despachados
    var viajesHeaders = ['ID Viaje', 'Placa Vehículo', 'Marca', 'Modelo', 'Serie Motor', 'Tipo Motor', 'Cilindraje', 'Cédula Chofer', 'Nombre Chofer', 'Licencia Chofer', 'Cédula Pasajero', 'Nombre Pasajero', 'Teléfono Pasajero'];
    var viajesRows = (datos.viajes || []).map(function(v) {
        return [
            v.idViaje,
            v.placaCarro,
            v.marcaCarro,
            v.modeloCarro,
            v.serieMotor,
            v.tipoMotor,
            v.cilindrajeMotor ? v.cilindrajeMotor + ' cc' : '-',
            v.cedulaChofer,
            v.nombreChofer,
            v.licenciaChofer,
            v.cedulaPasajero,
            v.nombrePasajero,
            v.telefonoPasajero
        ];
    });
    XLSX.utils.book_append_sheet(wb, crearHojaEstilizada('Viajes Despachados', viajesHeaders, viajesRows), 'Viajes');

    // 2. Hoja: Vehículos (Carros)
    var carrosHeaders = ['Placa', 'Marca', 'Modelo / Año'];
    var carrosRows = (datos.carros || []).map(function(c) {
        return [c.placa, c.marca, c.modelo];
    });
    XLSX.utils.book_append_sheet(wb, crearHojaEstilizada('Flota de Vehículos (Carros)', carrosHeaders, carrosRows), 'Carros');

    // 3. Hoja: Motores
    var motoresHeaders = ['Número de Serie', 'Tipo de Motor', 'Cilindraje'];
    var motoresRows = (datos.motores || []).map(function(m) {
        return [m.numeroSerie, m.tipo, m.cilindraje ? m.cilindraje + ' cc' : '-'];
    });
    XLSX.utils.book_append_sheet(wb, crearHojaEstilizada('Especificaciones de Motores', motoresHeaders, motoresRows), 'Motores');

    // 4. Hoja: Choferes
    var choferesHeaders = ['Cédula', 'Nombre Completo', 'Número de Licencia'];
    var choferesRows = (datos.choferes || []).map(function(ch) {
        return [ch.cedula, ch.nombre, ch.licencia];
    });
    XLSX.utils.book_append_sheet(wb, crearHojaEstilizada('Conductores Autorizados', choferesHeaders, choferesRows), 'Choferes');

    // 5. Hoja: Pasajeros
    var pasajerosHeaders = ['Cédula', 'Nombre', 'Apellido', 'Teléfono'];
    var pasajerosRows = (datos.pasajeros || []).map(function(p) {
        return [p.cedula, p.nombre, p.apellido, p.telefono];
    });
    XLSX.utils.book_append_sheet(wb, crearHojaEstilizada('Directorio de Pasajeros', pasajerosHeaders, pasajerosRows), 'Pasajeros');

    // Descargar libro consolidado
    var nombreArchivo = 'Reporte_General_Tienda_Parking_' + fechaSlug + '.xlsx';
    XLSX.writeFile(wb, nombreArchivo);
}
